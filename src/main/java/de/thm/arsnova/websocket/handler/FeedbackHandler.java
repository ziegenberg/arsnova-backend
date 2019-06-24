package de.thm.arsnova.websocket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import de.thm.arsnova.websocket.message.CreateFeedback;
import de.thm.arsnova.websocket.message.GetFeedback;

@Controller
public class FeedbackHandler {
	private final FeedbackCommandHandler commandHandler;

	@Autowired
	public FeedbackHandler(FeedbackCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	@MessageMapping("/queue/{roomId}.feedback.command")
	public void send(
			@DestinationVariable("roomId") String roomId,
			CreateFeedback value
	) throws Exception {

		commandHandler.handle(
				new FeedbackCommandHandler.CreateFeedbackCommand(roomId, value)
		);

	}

	@MessageMapping("/queue/{roomId}.feedback.query")
	public void send(
			@DestinationVariable("roomId") String roomId,
			GetFeedback value
	) throws Exception {

		commandHandler.handle(
				new FeedbackCommandHandler.GetFeedbackCommand(roomId, value)
		);

	}

}
