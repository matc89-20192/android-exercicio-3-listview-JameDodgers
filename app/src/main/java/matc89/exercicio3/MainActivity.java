package matc89.exercicio3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editDescricao;
    private EditText editPrioridade;
    private Button buttonAdicionar;
    private Button buttonRemover;
    private ListView listView;
    private TarefaAdapter tarefaAdapter;
    private ArrayList<Tarefa> tarefas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editPrioridade = (EditText) findViewById(R.id.editPrioridade);
        buttonAdicionar = (Button) findViewById(R.id.buttonAdicionar);
        buttonRemover = (Button) findViewById(R.id.buttonRemover);

        if(tarefas.isEmpty()){
            buttonRemover.setEnabled(false);
        }

        tarefaAdapter = new TarefaAdapter(this, tarefas);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(tarefaAdapter);

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tarefas.isEmpty()){
                    buttonRemover.setEnabled(true);
                }

                String descricao = editDescricao.getText().toString();
                int prioridade = Integer.parseInt(editPrioridade.getText().toString());

                if(validarTarefa(descricao, prioridade)) {
                    Tarefa tarefa = new Tarefa(descricao, prioridade);
                    tarefas.add(tarefa);
                    tarefaAdapter.notifyDataSetChanged();
                }

                sortArrayList();
            }
        });

        buttonRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tarefas.remove(0);
                tarefaAdapter.notifyDataSetChanged();
                if(tarefas.isEmpty()){
                    buttonRemover.setEnabled(false);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
            Tarefa tarefa = (Tarefa) listView.getItemAtPosition(i);
                tarefas.remove(tarefa);
                tarefaAdapter.notifyDataSetChanged();
            }
        });
    }

    public void sortArrayList(){
        Collections.sort(tarefas, new Comparator<Tarefa>() {
            @Override
            public int compare(Tarefa tarefa1, Tarefa tarefa2) {
                if(tarefa1.getPrioridade() < tarefa2.getPrioridade()){
                    return -1;
                }else if(tarefa1.getPrioridade() > tarefa2.getPrioridade()){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        tarefaAdapter.notifyDataSetChanged();
    }

    public boolean validarTarefa(String descricao, int prioridade){
        if(prioridade < 1 || prioridade > 10){
            Toast.makeText(this, "A prioridade deve estar entre 1 e 10.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            for(Tarefa tarefa : tarefas){
                if(descricao.equals(tarefa.getDescricao())){
                    Toast.makeText(this, "Tarefa já cadastrada.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }
}
