package br.com.nicoletti.busapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.busapi.beans.lottery.vo.BetVO;

@Repository
public interface BetDAO extends JpaRepository<BetVO, Integer> {

}
