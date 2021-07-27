package com.osmosis.jeopardyserver.configurations;

import com.osmosis.jeopardyserver.exceptions.AccessorException;
import com.osmosis.jeopardyserver.exceptions.PlayerNotRegisteredException;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketInterceptor implements ChannelInterceptor, HandshakeInterceptor {
	private final PlayerService playerService;

	public WebSocketInterceptor(PlayerService playerService) {
		this.playerService = playerService;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor =
				MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor == null) {
			throw new AccessorException("Message does not contain a stomp header accessor");
		}
		if (accessor.getCommand() == StompCommand.CONNECT) {
			Map<String, Object> attributes = accessor.getSessionAttributes();
			if (attributes == null) {
				throw new AccessorException("Accessor does not have attributes");
			}
			Object nicknameObject = attributes.get("nickname");
			if (!(nicknameObject instanceof String)) {
				throw new AccessorException("Accessor does not have the attribute 'nickname'");
			}
			String nickname = (String) nicknameObject;
			accessor.setUser(() -> nickname);
		}
		return ChannelInterceptor.super.preSend(message, channel);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
								   ServerHttpResponse response,
								   WebSocketHandler handler,
								   Map<String, Object> attributes) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String id = servletRequest.getServletRequest().getSession().getId();
			if (!playerService.containsId(id)) {
				throw new PlayerNotRegisteredException("Cannot connect web socket: Player is not registered");
			}
			String nickname = playerService.getPlayer(id).getNickname();
			attributes.put("nickname", nickname);
			return true;
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
							   ServerHttpResponse response,
							   WebSocketHandler handler,
							   Exception exception) {

	}
}
