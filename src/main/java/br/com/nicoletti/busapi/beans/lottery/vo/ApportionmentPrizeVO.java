package br.com.nicoletti.busapi.beans.lottery.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "detail_prize")
public class ApportionmentPrizeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_prize_id")
	private Long id;

	private Integer winners;

	private BigDecimal prizeValue;

	private Integer track;

	@ManyToOne
	@JoinColumn(name = "fk_game_resultion_id")
	private GameResultVO gameResult;

	public Integer getWinners() {
		return winners;
	}

	public void setWinners(Integer winners) {
		this.winners = winners;
	}

	public BigDecimal getPrizeValue() {
		return prizeValue;
	}

	public void setPrizeValue(BigDecimal prizeValue) {
		this.prizeValue = prizeValue;
	}

	public Integer getTrack() {
		return track;
	}

	public void setTrack(Integer track) {
		this.track = track;
	}

	public Long getId() {
		return id;
	}

	public GameResultVO getGameResultVO() {
		return gameResult;
	}

	public void setGameResultVO(GameResultVO gameResult) {
		this.gameResult = gameResult;
	}

}
