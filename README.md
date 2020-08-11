# Days

<h1>Library to achieve the difference between 2 dates.
Biblioteca para cálculo da diferença entre duas datas.</h1>

Calculate the amount of days, weekdays, weekends, working days and holidays.
Calcula quantidade de dias corridos, dias de semana, fins de semana, dias úteis e feriados (fixos e móveis).

<h2>Esta função Java é uma versão aperfeiçoada de uma proc que eu criei enquanto trabalhava no Unibanco (sim, antes da fusão com o Itaú, em 2008).
This Java function it's an enhanced version of a proc I've created in SQL when I've worked for Unibanco (yes, before Itau merge in 2008).</h2> 

Eu calculava os feriados móveis que derivam da Páscoa, ou seja, Carnaval, Corpus Christ e Sexta-Feira Santa.
O objetivo era não precisar atualizar uma tabela de feriados com estes dias, pois alguns processos de contagem de dias úteis (para limite de pagamentos de sinistros) precisavam disso e não podiam depender de atualização manual de informações.

I've calculated the changing holidays of every year, thos that depends on the Easter Day, which means Carnival, Corpus Christ and Holy Friday.
The main goal here it was don't need a holiday tables with this every-year-changing-days, once some couting work days processes (to compliance with SLA limit for Claims payments) need this and couldn't depending on manual information updates.


<h3>Anos depois 
Years after</h3>
Meu amigo @mtakeshi, quando conversamos sobre isso anos depois, na BV Financeira, resolveu fazer uma versão da função com alguns upgrades. Já existia Github e este foi o primeiro compartilhamento de código que eu usei por aqui.
