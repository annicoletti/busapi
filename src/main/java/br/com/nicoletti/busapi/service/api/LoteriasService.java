package br.com.nicoletti.busapi.service.api;

import java.util.Map;

import br.com.nicoletti.busapi.beans.lottery.to.GameResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.RaffleTO;
import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.exception.BusException;

public interface LoteriasService {

	/**
	 * Retorna o resultado completo e detalhado do concurso escolhido. Se o numero
	 * do sorteio não for informado será retornado o resultado mais recente.
	 * 
	 * Não é utilizado nenhuma cache, consulta é totalmente online.
	 * 
	 * @param {@link RaffleTO}
	 * @return {@link ResponseSuccessTO }
	 */
	ResponseTO getSingleResultOnline(RaffleTO raffleTO);

	/**
	 * Retorna o resultado completo e detalhado do concurso escolhido. É obrigatório
	 * o numero do sorteio.
	 * 
	 * Reultado é baseado em cache.
	 * 
	 * @param sorteioTO
	 * @return
	 */
	ResponseTO getSingleResultCached(RaffleTO sorteioTO);

	ResponseTO getMostRepeatedNumbers(Map<String, Object> params);

	GameResultTO getLotteryGameResultOnline(String tipoJogo, Integer numero) throws BusException;

	GameResultVO parseGameResultTOtoVO(GameResultTO dto);

	/**
	 * 
	 * @param tipoJogo
	 * @param numeroSorteio
	 * @return
	 * @throws BusException
	 */
	String getUrl(String tipoJogo, Integer numeroSorteio) throws BusException;

	/**
	 * Gera um sorteio baseado nos numeros mais repetidos do intervalo de tempo
	 * 
	 * 
	 * @param params
	 * @return
	 */
	ResponseTO generateRaffleNumbers(Map<String, Object> params);

	/**
	 * Verifica se o numero enviado foi sorteado, caso o valor {@link Contest} for
	 * nulo verifica se o valor já foi sorteado em algum concurso
	 * 
	 * @param GameType {@link String}
	 * @param Contest  {@link Integer}
	 * @param Numbers  {@link List<{@link Integer> numbers}
	 * @return
	 */
	ResponseTO checkResult(Map<String, Object> params);

	ResponseTO checkResultResume(Map<String, Object> params);

	ResponseTO generateRandomNumbers(Map<String, Object> params);

	/**
	 * 
	 * 
	 * @param params
	 * @return
	 */
	ResponseTO doBet(Map<String, Object> params);

	ResponseTO getChart(String game);

	ResponseTO getRatioCoefficient(String game);

}
