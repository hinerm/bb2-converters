/*
 * #%L
 * Blood Brothers 2 Converters.
 * %%
 * Copyright (C) 2015 Blood Brothers 2 Wiki Members
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.bb2;

public class WikiUtils {

	public static String getAffinity(final String affinity) {
		if (affinity.equals("2")) return "Melee";
		else if (affinity.equals("3")) return "Ranged";
		else if (affinity.equals("4")) return "Cavalry";

		throw new IllegalArgumentException("Invalid Affinity: " + affinity);
	}

	public static String getRace(final String race) {
		if (race.equals("1")) return "Goblin";
		else if (race.equals("2")) return "Darklander";
		else if (race.equals("3")) return "Lizardman";
		else if (race.equals("4")) return "Dwarf";
		else if (race.equals("5")) return "Westerner";
		else if (race.equals("6")) return "Easterner";
		else if (race.equals("7")) return "Undead";
		else if (race.equals("8")) return "Highlander";
		else if (race.equals("9")) return "Ape";
		else if (race.equals("10")) return "Paragon";
		else if (race.equals("11")) return "Champion";

		throw new IllegalArgumentException("Invalid Race: " + race);
	}

	public static String getSkillStat(final String skillStat) {
		if (skillStat.equals("Attack")) return "ATK";
		if (skillStat.equals("Wisdom")) return "WIS";

		throw new IllegalArgumentException("Invalid Skill Stat: " + skillStat);
	}
}
