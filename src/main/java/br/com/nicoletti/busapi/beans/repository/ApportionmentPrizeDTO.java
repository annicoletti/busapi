package br.com.nicoletti.busapi.beans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nicoletti.busapi.beans.lottery.vo.ApportionmentPrizeVO;

@Repository
public interface ApportionmentPrizeDTO extends JpaRepository<ApportionmentPrizeVO, Long> {

}
