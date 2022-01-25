package br.com.nicoletti.busapi.utils;

public interface LoteriasConstants {

	String SERVER = "http://loterias.caixa.gov.br";

	String PATH_MEGASENA = "/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage";

	String PATH_LOTOFACIL = "/wps/portal/loterias/landing/lotofacil/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbz8vTxNDRy9_Y2NQ13CDA0sTIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wBmoxN_FydLAGAgNTKEK8DkRrACPGwpyQyMMMj0VAcySpRM!/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_61L0H0G0J0VSC0AC4GLFAD2003/res/id=buscaResultado/c=cacheLevelPage";

	String FILTER = "/p=concurso=%s";

	/**
	 * Tipo/Nome do Jogo, megasena, lotofacil..
	 */
	String REQUEST_PARAMS_GAME_TYPE = "gameType";

	/**
	 * Quantidade de numeros que deseja trazer para realizar o jogo
	 */
	String REQUEST_PARAMS_PLAY_WITH = "playWith";

	/**
	 * Data de inicio para geração do cálculo dos numeros dos sorteios.
	 */
	String REQUEST_PARAMS_STARTS_IN = "startsIn";

	String REQUEST_PARAMS_CONTEST_NUMBER = "contest";

	String REQUEST_PARAMS_NUMBERS_FOR_CHECK = "numbersToCheck";

	String REQUEST_PARAMS_GAME_ATTEMP = "attemp";

	String REQUEST_PARAMS_GAME_NUMBERS_BET = "numbersBet";

	String REQUEST_PARAMS_GAME_RANDOM = "random";

//	public String PATH_GENERIC_GET_CURRENT_RESULT = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/%s/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbz8vTxNDRy9_Y2NQ13CDA0sTIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wBmoxN_FydLAGAgNTKEK8DkRrACPGwpyQyMMMj0VAcySpRM!/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_61L0H0G0J0VSC0AC4GLFAD2003/res/id=buscaResultado/c=cacheLevelPage/?timestampAjax=1634748924124";

}
