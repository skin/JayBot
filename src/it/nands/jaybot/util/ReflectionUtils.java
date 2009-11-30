package it.nands.jaybot.util;

import java.lang.reflect.Method;

/***
 * Classe di utilita' per la reflection
 * @author Alessandro Franzi
 *
 */
public class ReflectionUtils {
	
	/***
	 * Metodo per consultare le informazioni principali di una classe
	 * @param className	: nome della classe
	 */
	public static void showClassInfo(String className){
		try{
			Class classToIstanciate = Class.forName(className);
			Method methlist[] 
			                = classToIstanciate.getDeclaredMethods();
			for (int z = 0; z < methlist.length;
			z++) {  
				Method m = methlist[z];
				System.out.println("name = " + m.getName());
				System.out.println("decl class = " +
						m.getDeclaringClass());
				Class pvec[] = m.getParameterTypes();
				for (int j = 0; j < pvec.length; j++)
					System.out.println("param #" + j + " " + pvec[j]);
				Class evec[] = m.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.println("exc #" + j 
							+ " " + evec[j]);
				System.out.println("return type = " +
						m.getReturnType());
				System.out.println("-----");
			}
		 }catch(ClassNotFoundException ex){
			System.out.println("Eccezione : "+ex.getMessage());
		 }
	}
	
	/***
	 * Metodo mediante il quale e' possibile confrontare se 2 classi sono dello stesso tipo
	 * @param class1	: classe 1
	 * @param class2	: classe 2
	 * @return			: un boolean indicante se sono uguali
	 */
	public static boolean confrontateClasses(Class class1,Class class2){
		if (class1!=null && class2!=null){
			// se entrambe le classi sono diverse da null ha senso confrontarne i nomi
			String class1Name = class1.getName();
			String class2Name = class2.getName();
			if (class1Name.equals(class2Name)){
				return true;
			}
		}
		return false;
	}
	
	/***
	 * Metodo per controllare che una classe sia un implementazione di una determinata
	 * interfaccia
	 * @param classe			: classe da controlare
	 * @param interfaceClass	: interfaccia
	 * @return					: boolean indicante l'esito del controllo
	 */
	public static boolean checkIfClassIsImplementationOfAnInterface(Class classe,Class interfaceClass){
		if (classe!=null && interfaceClass!=null){
			Class[] interfaces = classe.getInterfaces();
			if (interfaces!=null){
				for (int z = 0 ; z<interfaces.length ; z++){
					Class superclass = interfaces[z];
					if (ReflectionUtils.confrontateClasses(interfaceClass, superclass)){								
						return true;
					}
				}
			}
		}
		return false;
	}
}
