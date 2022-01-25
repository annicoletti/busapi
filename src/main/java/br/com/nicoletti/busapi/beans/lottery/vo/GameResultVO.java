package br.com.nicoletti.busapi.beans.lottery.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "result_game")
public class GameResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer contest;

	private String gameType;

	private Date drawDate;

	private Integer previousContestNumber;

	private Integer nextContestNumber;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gameResult")
	private List<ApportionmentPrizeVO> apportionmentPrizes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gameResult")
	private List<RaffleNumberVO> tens;

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

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}

	public Integer getPreviousContestNumber() {
		return previousContestNumber;
	}

	public void setPreviousContestNumber(Integer previousContestNumber) {
		this.previousContestNumber = previousContestNumber;
	}

	public Integer getNextContestNumber() {
		return nextContestNumber;
	}

	public void setNextContestNumber(Integer nextContestNumber) {
		this.nextContestNumber = nextContestNumber;
	}

	public List<ApportionmentPrizeVO> getApportionmentPrizes() {
		return apportionmentPrizes;
	}

	public void setApportionmentPrizes(List<ApportionmentPrizeVO> apportionmentPrizes) {
		this.apportionmentPrizes = apportionmentPrizes;
	}

	public List<RaffleNumberVO> getTens() {
		return tens;
	}

	public void setTens(List<RaffleNumberVO> tens) {
		this.tens = tens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameResultVO other = (GameResultVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
