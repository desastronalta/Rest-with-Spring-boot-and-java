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
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		if(!numerico(numberOne) || !numerico(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value");
			
		}
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
		}
	private Double convertToDouble(String str) {
		if (str == null) {
			throw new UnsuportedMathOperationException("The String insert is null.");
			}
		String num = str.replaceAll(",", ".");
		if(numerico(num)) {
			return Double.parseDouble(num);
		}
		return 0D;
	}
	private boolean numerico(String strNumber) {
		if(strNumber == null) {
			return false;
		}
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[+-]?[0-9]*\\.?[0-9]+");
	}
	
}
