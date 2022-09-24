package br.com.nicoletti.busapi.beans.lottery.enums;

import br.com.nicoletti.busapi.exception.BusException;
import br.com.nicoletti.busapi.utils.BusExceptions;

public enum LotteryTypeGame {

	MEGASENA(0, "megasena", "MEGA_SENA", 6, 60), LOTOFACIL(1, "lotofacil", "LOTOFACIL", 15, 25);

	private Integer code;

	private String name;

	private String alternative;

	private Integer standardQuantity;

	private Integer numbersToRuffle;

	private LotteryTypeGame(Integer code, String name, String alternative, Integer standardQuantity,
			Integer numbersToRuffle) {
		this.code = code;
		this.name = name;
		this.alternative = alternative;
		this.standardQuantity = standardQuantity;
		this.numbersToRuffle = numbersToRuffle;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAlternative() {
		return alternative;
	}

	public Integer getStandardQuantity() {
		return standardQuantity;
	}

	public Integer getNumbersToRuffle() {
		return numbersToRuffle;
	}

	public static LotteryTypeGame toEnum(Object obj) throws BusException {
		if (obj instanceof String) {
			String typeGame = (String) obj;
			if (typeGame != null && !typeGame.isEmpty()) {
				for (LotteryTypeGame itemEnum : LotteryTypeGame.values()) {
					if (itemEnum.getName().equals(typeGame) || itemEnum.getAlternative().equals(typeGame)) {
						return itemEnum;
					}
				}
			}
		}

		throw new BusException(BusExceptions.LOTTERY_SERVICE_TYPE_GAME_IS_UNDEFINED, new String[] { obj.toString() });
	}

}
