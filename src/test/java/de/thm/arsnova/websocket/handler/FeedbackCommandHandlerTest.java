package de.thm.arsnova.websocket.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import de.thm.arsnova.websocket.message.CreateFeedback;
import de.thm.arsnova.websocket.message.CreateFeedbackPayload;
import de.thm.arsnova.websocket.message.FeedbackChanged;
import de.thm.arsnova.websocket.message.FeedbackChangedPayload;
import de.thm.arsnova.websocket.message.GetFeedback;

@RunWith(SpringRunner.class)
public class FeedbackCommandHandlerTest {

	@MockBean
	private SimpMessagingTemplate messagingTemplate;

	private FeedbackCommandHandler commandHandler;

	@Before
	public void setUp() {
		this.commandHandler = new FeedbackCommandHandler(messagingTemplate);
	}

	@Test
	public void getFeedback() {
		final String roomId = "12345678";
		final GetFeedback getFeedback = new GetFeedback();
		final FeedbackCommandHandler.GetFeedbackCommand getFeedbackCommand =
				new FeedbackCommandHandler.GetFeedbackCommand(roomId, null);

		commandHandler.handle(getFeedbackCommand);

		final FeedbackChangedPayload feedbackChangedPayload = new FeedbackChangedPayload();
		final int[] expectedVals = new int[]{0, 0, 0, 0};
		feedbackChangedPayload.setValues(expectedVals);
		final FeedbackChanged feedbackChanged = new FeedbackChanged();
		feedbackChanged.setPayload(feedbackChangedPayload);

		final ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<FeedbackChanged> messageCaptor =
				ArgumentCaptor.forClass(FeedbackChanged.class);

		verify(messagingTemplate).convertAndSend(topicCaptor.capture(), messageCaptor.capture());
		assertThat(topicCaptor.getValue()).isEqualTo("/topic/" + roomId + ".feedback.stream");
		assertThat(messageCaptor.getValue()).isEqualTo(feedbackChanged);
	}

	@Test
	public void sendFeedback() {
		final String roomId = "12345678";
		final CreateFeedbackPayload createFeedbackPayload = new CreateFeedbackPayload(1);
		createFeedbackPayload.setValue(1);
		final CreateFeedback createFeedback = new CreateFeedback();
		createFeedback.setPayload(createFeedbackPayload);
		final FeedbackCommandHandler.CreateFeedbackCommand createFeedbackCommand =
				new FeedbackCommandHandler.CreateFeedbackCommand(roomId, createFeedback);

		commandHandler.handle(createFeedbackCommand);

		final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(messagingTemplate).convertAndSend(captor.capture(), any(FeedbackChanged.class));
		assertThat(captor.getValue()).isEqualTo("/topic/" + roomId + ".feedback.stream");
	}
}

