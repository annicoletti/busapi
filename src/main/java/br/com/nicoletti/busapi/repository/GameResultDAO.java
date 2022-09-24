package br.com.nicoletti.busapi.repository;

import java.util.Date;
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

	List<GameResultVO> findByDrawDateBetweenAndGameType(Date init, Date end, String gameType);

	// @Query("SELECT g FROM GameResultVO g WHERE g.drawDate >= :init and g.drawDate
	// <= :end and g.gameType = :gameType")
//	List<GameResultVO> findByRangeDateAndGameType(@Param("init") Date init, @Param("end") Date end,
//			@Param("gameType") String gameType);

//	@Query("SELECT g FROM GameResultVO g WHERE g.drawDate >= ?1 and g.drawDate <= ?2 and g.gameType = ?3")
//	List<GameResultVO> findByRangeDateAndGameType(Date init, Date end, String gameType);

	// List<GameResultVO> findByGameTypeAndContest(String tipoJogo, Integer numero);

//	Optional<GameResultVO> findByGameTypeAndContest(String tipoJogo, Integer numero);

//	@Query("SELECT g FROM GameResultVO g WHERE g.gameType = ?1 and g.contest = ?2")
//	GameResultVO findByGametypeAndContest(String tipoJogo, Integer numero);

//	@Query("SELECT g FROM GameResultVO g WHERE g.gameType = :tipoJogo and g.contest = :numero")
//	List<GameResultVO> findByGametypeAndContest(String tipoJogo, Integer numero);

}
