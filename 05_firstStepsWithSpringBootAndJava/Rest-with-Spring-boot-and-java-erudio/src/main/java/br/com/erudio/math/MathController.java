package br.com.erudio.math;

//import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsuportedMathOperationException;

@RestController
public class MathController {
	//private final AtomicLong counter = new AtomicLong();
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
			method=RequestMethod.GET)
	//soma
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		validNum(numberOne,numberTwo);
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
		}
	
	//mutiplicação
	@RequestMapping(value = "/multiple/{numberOne}/{numberTwo}",
			method =RequestMethod.GET)
	public Double multiple(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		validNum(numberOne,numberTwo);
		return convertToDouble(numberOne) * convertToDouble(numberTwo);
	}
	
	@RequestMapping(value = "/divide/{numberOne}/{numberTwo}",
			method =RequestMethod.GET)
	public Double divide(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		validNum(numberOne,numberTwo);
		isZero(numberTwo);
		return convertToDouble(numberOne) / convertToDouble(numberTwo);
	}

	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}",
			method =RequestMethod.GET)
	public Double sub(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		validNum(numberOne,numberTwo);
		return convertToDouble(numberOne) - convertToDouble(numberTwo);
	}
	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}",
			method =RequestMethod.GET)
	public Double mean(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		validNum(numberOne,numberTwo);
		return (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
	}
	@RequestMapping(value = "/squareRoot/{number}",
			method =RequestMethod.GET)
	public Double squareRoot(
			@PathVariable(value = "number") String number
			) throws Exception{
		validNum(number);
		return Math.sqrt(convertToDouble(number));
	}
	
	private void isZero(String str) {
		if(Double.parseDouble(str) == 0.0000) {
			throw new UnsuportedMathOperationException("Divide for Zero is not possible");
		}
	}
	
	private void validNum(String numberOne, String numberTwo) {
		if(!numerico(numberOne) || !numerico(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value");
		}
	}
	private void validNum(String numberOne) {
		if(!numerico(numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value");
		}
	}
	private Double convertToDouble(String str) {
		if (str == null) {
			throw new UnsuportedMathOperationException("The valor insert is null.");
			}
		String num = converteVirgula(str);
		if(numerico(num)) {
			return Double.parseDouble(num);
		}
		return 0D;
	}
	
	
	private String converteVirgula(String str) {
		 
		return str.replaceAll(",", ".");
	}


	private boolean numerico(String strNumber) {
		if(strNumber == null) {
			return false;
		}
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[+-]?[0-9]*\\.?[0-9]+");
	}
	
}
