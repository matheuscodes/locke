# Locke

Locke - Video rental store management tool

Copyright (C) 2005 Matheus Borges Teixeira.

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/logo-carregando.png" />

## About

This tool models management of a video rental store. It controls some basic finances and lending of several different collections, in four different categories (CDs, Videos, Games and Books). A complete user management is also present. The tool uses simple, unprotected, text files for storing data. Developed and designed for a particular system while working at a rental store on night shifts. The intention of the tool was to simplify and facilitate the management of the store.

**License of the tool:** GPLv3.

**Language of the tool:** Portuguese.

**Language of the code and documentation:** English.

_Icon design by http://www.dryicons.com/_

# Manual de Instruções
Este é um programa simples para gerenciar locações.
Ele foi desenvolvido para a BiblioteCAASO.
O sistema é simples e permite organização dos acervos em 4 diferente categorias: CDs, Livros, Vídeos e Jogos.
Além do gerenciamento de usuários, o programa oferece lógica específica da BiblioteCAASO, como duplo endereço e gerenciamento de pontos bônus para desconto nas locações. Foi desenvolvido usando Java e toda a manutenção de dados é feita por arquivos de texto.

## Barra Lateral
<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/1.png" width="200px" align="right" />

É usada para alternar entre os painéis principais do programa.

- **CDs, Vídeos, Livros and Jogos:** Abre o painel de manutenção de acervos.
- **Usuários:** Abre o painel de manutenção de usuários da biblioteca.
- **Configurações:** Abre o painel de configuração da biblioteca.
- **Locações:** Abre o painel de manutenção das locações em aberto.
- **Movimentações:** Abre o painel de controle de caixa e histórico.

<br/>
<br/>
<br/>
<br/>

## Manutenção de Acervos

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/2.png" />

O painel é o mesmo para todos os tipos de acervo. A diferença está apenas na semântica do conteúdo, pois _CDs_ contêm **Álbum** e **Artista**, _Videos_ contêm **Título** e **Título Original**, _Jogos_ contêm **Título** e **Classificação**, todos com **Código** e _Livros_ que contêm **Autor**, **Título** e **Assunto**. Não é possível editar um item do acervo depois que o mesmo for adicionado, mas é possível remover. A lista de itens do acervo pode ser ordenada por qualquer um dos três parâmetros e também pelo número de retiradas.

## Manutenção de Usuários e Nova Locação

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/3.png" />

Assim como na manutenção de acervos, é apenas possível adicionar e remover usuários, não é possível editar informações depois de inseridas. Ao selecionar usuários, é possível observar todas as informações e também um histórico de locações efetuadas.

### Novo Cadastro

O botão **Novo Cadastro** abre uma nova janela para adicionar usuários:

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/4.png" />

### Nova Locação

O botão **Nova Locação** abre uma nova janela com as informações para criar uma locação:<br/>

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/5.png" />

Os acervos permitidos são apenas para referência, não existe nenhuma restrição de software para seleção de itens. A busca funciona por qualquer conjunto de caracteres, e encontrará apenas comparações exatas (sem substituição de caracteres especiais ou letras maiúsculas). É preciso buscar para adicionar um item para locação. Depois de adicionado, não é possível remover um item da locação. A data de devolução é calculada com base nas configurações da biblioteca para o item que contiver a data mais próxima. 

**Importante:** O cálculo da devolução é bem específico no sistema utilizado pela BiblioteCAASO, sendo sempre uma semana e caso a entrega seja no Sábado ou Domingo, é extendida até a próxima Segunda.

## Configuração da Biblioteca

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/6.png" />

Neste painel é possível remover e adicionar acervos dentro das quatro categorias e configurar, por categoria, quantas semanas o item pode ficar em uma locação.

## Manutenção de Locações

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/7.png" />

As locações são ordenadas por ordem em que foram feitas, e não pela data de devolução.

### Devolução

O botão devolução abre a seguinte janela:<br/>

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/8.png" />

É preciso inserir o valor que será cobrado do usuário para controle financeiro. A quantidade de **Marquinhas** (Pontos Bônus) são mostrados para referência. É possível adicionar marquinhas ou removê-las, colocando valores negativos.

## Controle de Caixa

<img src="https://raw.githubusercontent.com/matheuscodes/locke/master/resources/manual/9.png" />

Neste painel é possível ver o extrato de transações, creditar ou debitar valores no caixa da biblioteca. Todas as devoluções inserem uma nova entrada.
