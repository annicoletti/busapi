package br.com.nicoletti.busapi.beans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.busapi.beans.lottery.vo.ErrorCaseVO;

@Repository
public interface ErrorCaseDAO extends JpaRepository<ErrorCaseVO, Long> {

	ErrorCaseVO findByContest(Integer contest);

}
