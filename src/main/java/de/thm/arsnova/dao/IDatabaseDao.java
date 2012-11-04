/*
 * Copyright (C) 2012 THM webMedia
 *
 * This file is part of ARSnova.
 *
 * ARSnova is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.thm.arsnova.dao;

import java.util.List;

import de.thm.arsnova.entities.Answer;
import de.thm.arsnova.entities.Feedback;
import de.thm.arsnova.entities.FoodVote;
import de.thm.arsnova.entities.LoggedIn;
import de.thm.arsnova.entities.Question;
import de.thm.arsnova.entities.Session;
import de.thm.arsnova.entities.User;

public interface IDatabaseDao {
	void cleanFeedbackVotes(int cleanupFeedbackDelay);

	Session getSessionFromKeyword(String keyword);

	Session getSession(String keyword);

	List<Session> getMySessions(String username);

	Session saveSession(Session session);

	Feedback getFeedback(String keyword);

	boolean saveFeedback(String keyword, int value, User user);

	boolean sessionKeyAvailable(String keyword);

	boolean saveQuestion(Session session, Question question);

	Question getQuestion(String id, String sessionKey);

	List<Question> getSkillQuestions(String session);

	int getSkillQuestionCount(String sessionkey);

	LoggedIn registerAsOnlineUser(User u, Session s);

	void updateSessionOwnerActivity(Session session);

	Integer getMyFeedback(String keyword, User user);

	List<String> getQuestionIds(String sessionKey);

	void deleteQuestion(String sessionKey, String questionId);

	List<String> getUnAnsweredQuestions(String sessionKey);

	Answer getMyAnswer(String sessionKey, String questionId);

	List<Answer> getAnswers(String sessionKey, String questionId);

	int getAnswerCount(String sessionKey, String questionId);

	List<Answer> getFreetextAnswers(String sessionKey, String questionId);

	int countActiveUsers(long since);

	int countActiveUsers(Session session, long since);

	List<Answer> getMyAnswers(String sessionKey);

	int getTotalAnswerCount(String sessionKey);

	int getInterposedCount(String sessionKey);

	List<Question> getInterposedQuestions(String sessionKey);

	void vote(String menu);

	int getFoodVoteCount();

	List<FoodVote> getFoodVote();

	int countSessions();

	int countOpenSessions();

	int countClosedSessions();

	int countAnswers();

	int countQuestions();
}