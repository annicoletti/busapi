package br.com.nicoletti.busapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.busapi.beans.lottery.vo.RaffleNumberVO;

@Repository
public interface RaffleNumberDAO extends JpaRepository<RaffleNumberVO, Long> {

}
