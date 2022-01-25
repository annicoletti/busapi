package br.com.nicoletti.busapi.beans.lottery.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "raffle_number")
public class RaffleNumberVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number_ruffle_id")
	private Long id;

	private Integer number;

	private Integer sequence;

	@ManyToOne
	@JoinColumn(name = "fk_game_resultion_id")
	private GameResultVO gameResult;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public GameResultVO getGameResult() {
		return gameResult;
	}

	public void setGameResult(GameResultVO gameResult) {
		this.gameResult = gameResult;
	}

}
