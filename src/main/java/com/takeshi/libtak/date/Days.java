package com.takeshi.libtak.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Days {

	private Date dataInicial;
	private Date dataFinal;
	private Long dias;
	private Long diasSemana;
	private Long diasUteis;
	private Integer fds; 

	List<Date> holidays = new ArrayList<Date>();
	private static String[] feriados = {"01/01","21/04","01/05","07/09","12/10","02/11","15/11","25/12"};
	
	public void DaysBetween(Date dataInicial, Date dataFinal) throws Exception {
		System.out.println(dataInicial.compareTo(dataFinal));
		if (dataInicial.compareTo(dataFinal) < 0) {
			this.dataInicial = dataInicial;
			this.dataFinal = dataFinal;
		} else {
			this.dataInicial = dataFinal;
			this.dataFinal = dataInicial;
		}
		
		this.calculateHolidays();
		this.calculateDays();
	}
	
	public Integer getDays() {
		return (int)(long)dias;
	}
	
	public Integer getWeekDays() {
		return (int)(long)diasSemana;
	}

	public Integer getWeekends() {
		return fds;
	}
	
	public Integer getWorkingDays() {
		return (int)(long)diasUteis;
	}
	
	public List<Date> getHolidays() {
		return holidays;
	}
	
	
	private void calculateDays() throws Exception {
		
		Calendar ci = Calendar.getInstance();
		ci.setTime(this.dataInicial);
		
		Calendar cf = Calendar.getInstance();
		cf.setTime(this.dataFinal);				
		
		// dias entre as datas
		dias = (cf.getTimeInMillis() - ci.getTimeInMillis()) / ((long)1000*60*60*24);
		
		//-------------------------------------------------
		int wi = ci.get(Calendar.DAY_OF_WEEK);
		ci.add(Calendar.DAY_OF_WEEK, -wi);
		int wf = cf.get(Calendar.DAY_OF_WEEK);
		cf.add(Calendar.DAY_OF_WEEK, -wf);

		Long calcDias = (cf.getTimeInMillis() - ci.getTimeInMillis()) / ((long)1000*60*60*24);
		Long daysWithoutFds = calcDias-(calcDias/7*2);

		if ((wi-=1) == 6) { wi = 5; }
		if ((wf-=1) == 6) { wf = 5; }

		// dias de semana entre as datas
		diasSemana = (daysWithoutFds-wi+wf);
		
		//-------------------------------------------------
		fds = (int)(long)(calcDias/7);
		
		ci.setTime(dataInicial);
		if (ci.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			fds++;
		}
		
		cf.setTime(dataFinal);
		if (cf.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			fds++;
		}
		
		Calendar cal = Calendar.getInstance();
		diasUteis = diasSemana;
		for (Date d : holidays) {
			cal.setTime(d);
			int dow = cal.get(Calendar.DAY_OF_WEEK);
			
			if (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY) {
				diasUteis--;
			}
		}
		
	}
	
	
	private List<Date> calculateHolidays() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Calendar cal = Calendar.getInstance();

		cal.setTime(this.dataInicial);
		int ano1 = cal.get(Calendar.YEAR);
		
		cal.setTime(this.dataFinal);
		int ano2 = cal.get(Calendar.YEAR);
		
		for (int i = ano1; i <= ano2; i++) {
			for (String item : feriados) {
				String sData = item + "/" + i;
				Date feriado = sdf.parse(sData);
				addFeriado(feriado);
			}

			String dtPascoa = getPascoaMJB(i);
			Date pascoa = sdf.parse(dtPascoa);
			addFeriado(pascoa);
			
			Long lPaixao = pascoa.getTime() - (long)2 * 24 * 60 * 60 * 1000;
			Long lCarnaval = pascoa.getTime() - (long)47 * 24 * 60 * 60 * 1000;
			Long lChristi = pascoa.getTime() + (long)60 * 24 * 60 * 60 * 1000;

			Date data = new Date();
			data.setTime(lPaixao);
			addFeriado(data);
			
			data.setTime(lCarnaval);
			addFeriado(data);
			
			data.setTime(lChristi);
			addFeriado(data);
		}
		
		return holidays;
	}
	
	private void addFeriado(Date feriado) {
		Long f = feriado.getTime();
		Long di = this.dataInicial.getTime();
		Long df = this.dataFinal.getTime();
		
		if (di <= f && f <= df) {
			holidays.add((Date)feriado.clone());
		}
	}
	
	private static String getPascoaGauss(Integer ano) {
		int dia = 0;
		int mes = 0;
		
		int x = 0;
		int y = 0;
		
		if (ano >= 1582 && ano < 1700) {
			x = 22;
			y = 2;
		} else if (ano >= 1700 && ano < 1800) {
			x = 23;
			y = 3;
		} else if (ano >= 1800 && ano < 1900) {
			x = 23;
			y = 4;
		} else if (ano >= 1900 && ano < 2100) {
			x = 24;
			y = 5;
		} else if (ano >= 2100 && ano < 2200) {
			x = 24;
			y = 6;
		} else if (ano >= 2200 && ano < 2300) {
			x = 25;
			y = 7;
		}

		int a = ano % 19;
		int b = ano % 4;
		int c = ano % 7;
		int d = (19 * a + x) % 30;
		int e = (2 * b + 4 * c + 6 * d + y ) % 7;

		if ((d + e) > 9) {
			dia = (d + e - 9);
			mes = 4;
		} else {
			dia = (d + e + 22);
			mes = 3;
		}

		if (dia == 26 && mes == 4) { //excecao para ano 2076
			dia = 19;
		}

		if (dia == 25 && mes == 4 && d == 28 && a > 10) {	//excecao para ano 2049
			dia = 18;
		}

		return ( dia + "/" + mes + "/" + ano );
	}
	
	private static String getPascoaMJB(Integer ano) {	//metodo Meeus-Jones-Butcher
		int dia = 0;
		int mes = 0;
		
		int a = ano % 19;
		int b = ano / 100;
		int c = ano % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		
		mes = (h + l - 7 * m + 114) / 31;
		dia = ((h + l - 7 * m + 114) % 31) + 1;
		
		return ( dia + "/" + mes + "/" + ano );
	}
	
}
