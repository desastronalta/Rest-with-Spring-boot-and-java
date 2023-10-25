package br.com.erudio.greeting;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/*
 * @RestController é a junção entre Controller e ResponseBody sendo adicionada para facilitar o desenvolvimento de serviços
 * com o Spring.
 */
@RestController
public class GreetingController {
	
	private static final String template =	"Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	/*
	 * O RequestMapping serve para mapear o caminho até o método que é passado através
	 * de um endereço HTTP.
	 */
	@RequestMapping("/greeting")
	/*
	 * o RequestParam é um parametro opcional tbm conhecido como querry param
	 * e através dele alteramos o value de name. E quando não é passado um parametro
	 * ele usa o valor padrão que neste caso é World.
	 */
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World")
	String name) {
		return new Greeting(counter.incrementAndGet(),String.format(template,name));
	}
	
}
