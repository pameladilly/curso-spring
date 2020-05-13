import React from 'react'
import { withRouter } from 'react-router-dom'
import Card from '../../components/card'
import FormGroup from '../../components/form-group'
import SelectMenu from '../../components/selectMenu'
import LancamentosTable from './lancamentosTable'
import LocalStorageService from '../../app/service/localstorageService'
import LancamentoService from '../../app/service/lancamentoService'
import * as messages from '../../components/toastr'

class ConsultaLancamentos extends React.Component {


    constructor() {
        super();
        this.service = new LancamentoService()
    }

    state = {
        ano: '',
        mes: '',
        tipo: '',
        descricao: '',
        lancamentos: []

    }

    buscar = () => {

        if (!this.state.ano) {
            messages.mensagemErro('O preenchimento do campo Ano é obrigatório.')
            return false
        }
        const usuarioLogado = LocalStorageService.obterItem('_usuario_logado')

        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service
            .consultar(lancamentoFiltro)
            .then(response => {
                console.log(response.data)
                this.setState({ lancamentos: response.data })

            }).catch(error => {
                console.log(error)
            })
    }

    editar = (id) => {
        console.log('Editando o lançamento', id)
    }

    deletar = (id) => {
        console.log('Deletando o lançamento', id)
    }

    render() {
        const meses = this.service.obterListaMeses()
        const tipos = this.service.obterListaTipos()



        return (
            <Card title="Consulta Lançamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup htmlFor="inputAno" label="Ano: *">
                                <input type="text"
                                    className="form-control"
                                    id="inputAno"
                                    aria-describedby="emailHelp"
                                    value={this.state.ano}
                                    onChange={e => this.setState({ ano: e.target.value })}
                                    placeholder="Digite o Ano" />

                            </FormGroup>
                            <FormGroup htmlFor="inputMes" label="Mês:">
                                <SelectMenu id="inputMes"
                                    className="form-control"
                                    value={this.state.mes}
                                    onChange={e => this.setState({ mes: e.target.value })}
                                    lista={meses} />
                            </FormGroup>
                            <FormGroup htmlFor="inputDesc" label="Descrição:">
                                <input type="text"
                                    className="form-control"
                                    id="inputDesc"
                                    aria-describedby="emailHelp"
                                    value={this.state.descricao}
                                    onChange={e => this.setState({ descricao: e.target.value })}
                                    placeholder="Digite a Descrição" />

                            </FormGroup>
                            <FormGroup htmlFor="inputTipo" label="Tipo de Lançamento:">
                                <SelectMenu id="inputTipo"
                                    className="form-control"
                                    value={this.state.tipo}
                                    onChange={e => this.setState({ tipo: e.target.value })}
                                    lista={tipos} />
                            </FormGroup>

                            <button onClick={this.buscar} type="button" className="btn btn-success">Buscar</button>
                            <button type="button" className="btn btn-danger" >Cadastrar</button>

                        </div>
                    </div>
                </div>
                <br />
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <LancamentosTable
                                lancamentos={this.state.lancamentos}
                                deleteAction={this.deletar}
                                editAction={this.editar} />
                        </div>
                    </div>
                </div>
            </Card >
        )
    }

}

export default withRouter(ConsultaLancamentos)