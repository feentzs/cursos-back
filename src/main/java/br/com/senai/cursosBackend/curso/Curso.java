package br.com.senai.cursosBackend.curso;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "curso")
@Entity(name = "Curso")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;


    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    private boolean ativo;

    public Curso(@Valid DadosCadastroCurso dados) {
        this.nome = dados.nome();
        this.periodo = dados.periodo();
        this.ativo = true;
    }


    public void atualizarCurso(DadosAtualizarCurso dados) {
        if(dados.nome() !=null && !dados.nome().isBlank()){
            this.nome = dados.nome();
        }
        if(dados.periodo() != null){
           this.periodo = dados.periodo();
        }
    }

    public void excluirCurso() {
        ativo = false;
    }
}
