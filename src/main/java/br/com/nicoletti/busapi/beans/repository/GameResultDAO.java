package br.com.nicoletti.busapi.beans.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;

@Repository
public interface GameResultDAO extends JpaRepository<GameResultVO, Long> {

	@Query(countQuery = "SELECT count(*) FROM GameResultVO g WHERE g.gameType = ?1")
	Long countByGameType(String gameType);

	List<GameResultVO> findAllByGameType(String gameType);

	GameResultVO findByGameTypeAndContest(String tipoJogo, Integer numero);

	// List<GameResultVO> findByGameTypeAndContest(String tipoJogo, Integer numero);

//	Optional<GameResultVO> findByGameTypeAndContest(String tipoJogo, Integer numero);

//	@Query("SELECT g FROM GameResultVO g WHERE g.gameType = ?1 and g.contest = ?2")
//	GameResultVO findByGametypeAndContest(String tipoJogo, Integer numero);

//	@Query("SELECT g FROM GameResultVO g WHERE g.gameType = :tipoJogo and g.contest = :numero")
//	List<GameResultVO> findByGametypeAndContest(String tipoJogo, Integer numero);

}
