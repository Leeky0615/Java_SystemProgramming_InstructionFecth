package processManager;

public class Interrupt {
	public enum EInterrupt {eNone,eProcessStart, eProcessTerminated, eTimerStart, eTimerFinished, eIOStart, eIOFinished}

	private EInterrupt eType;
	private Object parameter;

	public Interrupt(EInterrupt eType, Object parameter) {
		this.eType = eType;
		this.parameter = parameter;
	}
	public EInterrupt geteType() {return eType;}
	public void seteType(EInterrupt eType) {this.eType = eType;}
	public Object getParameter() {return parameter;}
	public void setParameter(Object parameter) {this.parameter = parameter;}
}