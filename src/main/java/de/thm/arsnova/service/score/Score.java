/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2019 The ARSnova Team and Contributors
 *
 * ARSnova Backend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova Backend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.thm.arsnova.service.score;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Calculates the score for users and their sessions.
 */
public class Score implements Iterable<QuestionScore> {

	private final Map<String, QuestionScore> scores;

	public Score() {
		this(new HashMap<String, QuestionScore>());
	}

	public Score(final Map<String, QuestionScore> theScores) {
		this.scores = theScores;
	}

	public void addQuestion(
			final String questionId, final String questionVariant, final int piRound, final int questionScore) {
		if (questionScore == 0) {
			return;
		}
		scores.put(questionId, new QuestionScore(questionId, questionVariant, piRound, questionScore));
	}

	public void addAnswer(final String questionId, final int piRound, final String userId, final int userscore) {
		if (!scores.containsKey(questionId)) {
			// Precondition failed, ignore this element.
			// Most likely this is a question that has no score value.
			return;
		}
		if (userId == null || userId.isEmpty()) {
			// Precondition failed: ignore anonymous users
			return;
		}
		final QuestionScore questionScore = scores.get(questionId);
		questionScore.add(piRound, userId, userscore);
	}

	public Score filterVariant(final String questionVariant) {
		final Map<String, QuestionScore> newScores = new HashMap<>();
		for (final Entry<String, QuestionScore> entry : this.scores.entrySet()) {
			final String questionId = entry.getKey();
			final QuestionScore questionScore = entry.getValue();
			if (questionScore.isVariant(questionVariant)) {
				newScores.put(questionId, questionScore);
			}
		}
		return new Score(newScores);
	}

	public int getMaximumScore() {
		int score = 0;
		for (final QuestionScore questionScore : this) {
			score += questionScore.getMaximum();
		}
		return score;
	}

	public int getTotalUserScore() {
		int score = 0;
		for (final QuestionScore questionScore : this) {
			score += questionScore.getTotalUserScore();
		}
		return score;
	}

	public double getTotalUserScore(final String userId) {
		int score = 0;
		for (final QuestionScore questionScore : this) {
			score += questionScore.getTotalUserScore(userId);
		}
		return score;
	}

	public int getTotalUserCount() {
		final Set<String> users = new HashSet<>();
		for (final QuestionScore questionScore : this) {
			questionScore.collectUsers(users);
		}
		return users.size();
	}

	public int getQuestionCount() {
		return scores.size();
	}

	@Override
	public Iterator<QuestionScore> iterator() {
		return this.scores.values().iterator();
	}
}
