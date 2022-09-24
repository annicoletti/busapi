package br.com.nicoletti.busapi.beans.lottery.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

@Entity(name = "error_case")
public class ErrorCaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer contest;

	private String lotteryTypeGame;

	private Date lastAttemp;

	@Column(columnDefinition = "text")
	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getContest() {
		return contest;
	}

	public void setContest(Integer contest) {
		this.contest = contest;
	}

	public String getLotteryTypeGame() {
		return lotteryTypeGame;
	}

	public void setLotteryTypeGame(String lotteryTypeGame) {
		this.lotteryTypeGame = lotteryTypeGame;
	}

	public Date getLastAttemp() {
		return lastAttemp;
	}

	public void setLastAttemp(Date lastAttemp) {
		this.lastAttemp = lastAttemp;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
