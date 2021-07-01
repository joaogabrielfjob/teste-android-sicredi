<h1 align="center">
  <img src="https://i.imgur.com/B0kfF2t.png" /> <br/>
  
  <img src="https://img.shields.io/badge/kotlin-1.5.20-orange?link=https://kotlinlang.org/&link=http://right" />
  <img src="https://img.shields.io/badge/gradle-4.2.1-lightgrey" />
  <img src="https://img.shields.io/badge/sdk-19-green" />
</h1>

# Sobre
O Evnts é um aplicativo utilizado para realizar check-in em eventos e também compartilhá-lhos com contatos.

# Arquitetura
Para a arquitetura eu optei por seguir a estrutura do Model View ViewModel (MVVM) já previamente anunciada pela própria Google como o modelo a ser seguido durante o Google I/O de 2017, onde também foi anunciado novas features e um suporte maior para o ViewModel. Além do mais, com o MVVM temos uma melhor organização do código e uma separação de responsabilidades bem definida, o que acaba viabilizando as manutenções no futuro.

# Recursos
<span>
  <h3>Retrofit</h3> 
  <img src="https://img.shields.io/badge/Retrofit-2.9.0-brightgreen" /> <br/>
  
  O Retrofit possui uma curva de aprendizado bem pequena, além de ser bem customizável, pois para manipular dados JSON você pode utilizar a bem conhecida biblioteca Gson, mas se     precisar também é possível adicionar outros converters para trabalhar com XML e outros mais.
  
  <h3>Glide</h3> 
  <img src="https://img.shields.io/badge/Glide-4.12.0-9cf" /> <br/>
  
   Para o carregamento das imagens optei por utilizar o Glide, que tem uma alta eficiência ao scrollar as imagens, devido ao carregamento prévio delas na memória. Inclusive a        própria Google anunciou apoio a ferramenta e a decretou como uma boa escolha para o carregamento de imagens.  
</span>

# Demonstração
![Demo Evnts Light Theme](https://user-images.githubusercontent.com/37043322/124142933-0c6bb400-da61-11eb-9965-5cda2f455efd.gif)
![Demo Evnts Dark Theme](https://user-images.githubusercontent.com/37043322/124166123-e225f080-da78-11eb-87f0-6019ac287692.gif)
> **Observação:** Como isso é um GIF as cores não são 100% fiéis
