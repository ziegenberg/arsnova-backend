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

/**
 * The learning progress answer score of a particular user.
 */
public class UserScore {

	private String userId;

	private int score;

	public UserScore(final String userId, final int score) {
		this.userId = userId;
		this.score = score;
	}

	public boolean hasScore(final int score) {
		return this.score == score;
	}

	public int getScore() {
		return score;
	}

	public boolean isUser(final String userId) {
		return this.userId.equals(userId);
	}

	public String getUserId() {
		return userId;
	}
}
