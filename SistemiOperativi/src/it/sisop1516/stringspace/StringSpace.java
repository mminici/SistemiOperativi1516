package it.sisop1516.stringspace;

public abstract class StringSpace {
		protected int length;
		protected String currentString;
		/**
		 * @author marco
		 * @throws ExceededStringBoundException because space is limited
		 */
		public abstract void nextString() throws ExceededStringBoundException;
		
		public String getString(){return currentString;}
		
		public int getLength(){return length;}
		
		
}
