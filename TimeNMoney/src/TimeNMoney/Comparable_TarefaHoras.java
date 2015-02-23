package TimeNMoney;

public class Comparable_TarefaHoras implements Comparable<Comparable_TarefaHoras> {
	private String id;
	private TarefaHoras tarefa;
	
	public Comparable_TarefaHoras(String s, TarefaHoras t){
		this.id = s;
		this.tarefa = t;
	}

	public TarefaHoras get_tarefas(){
		return this.tarefa;
	}
	
	@Override
	public int compareTo(Comparable_TarefaHoras o) {
		if (this.id.compareTo(o.id)<0)
			return -1;
		else if (this.id.compareTo(o.id)>0)
			return 1;
		else
			return 0;
	}
	
	
}
