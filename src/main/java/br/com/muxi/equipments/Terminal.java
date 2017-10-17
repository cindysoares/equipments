package br.com.muxi.equipments;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.muxi.equipments.exception.EquipmentsApiException;

@Entity
public class Terminal {
	
	@Id
	@JsonProperty(required=true)
	private Integer logic;
	@NotNull
	@JsonProperty(required=true)
	private String serial;
	@NotNull
	@JsonProperty(required=true)
	private String model;
	@Min(0)
	private Integer sam;
	private String ptid;
	private Integer plat;
	@NotNull
	@JsonProperty(required=true)
	private String version;
	private Integer mxr;
	private Integer mxf;	
	private String verfm;
	
	public Terminal() {
	}
	
	public Terminal(Integer logic, String serial, String model, Integer sam, String ptid, 
			Integer plat, String version, Integer mxr, Integer mxf, String verfm) {
		this(logic);
		this.serial = serial;
		this.model = model;
		this.sam = sam;
		this.ptid = ptid;
		this.plat = plat;
		this.version = version;
		this.mxr = mxr;
		this.mxf = mxf;
		this.verfm = verfm;
	}
	
	public Terminal(Integer logic) {
		this.logic = logic;
	}
	
	public Integer getLogic() {
		return logic;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public Integer getSam() {
		return sam;
	}

	public void setSam(Integer sam) {
		this.sam = sam;
	}

	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public Integer getPlat() {
		return plat;
	}

	public void setPlat(Integer plat) {
		this.plat = plat;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getMxr() {
		return mxr;
	}

	public void setMxr(Integer mxr) {
		this.mxr = mxr;
	}
	
	public Integer getMxf() {
		return mxf;
	}
	
	public void setMxf(Integer mxf) {
		this.mxf = mxf;
	}

	@JsonProperty("VERFM")
	public String getVerfm() {
		return verfm;
	}
	
	public void setVerfm(String verfm) {
		this.verfm = verfm;
	}
	
	public static Terminal valueOf(String arguments) throws EquipmentsApiException {
		String[] values = arguments.split(";");
		
		Integer logic = parseValueToInt("logic", values, 0);
		
		Terminal parsedTerminal =  new Terminal(logic, getValue(values, 1), 
				getValue(values, 2),
				parseValueToInt("sam", values, 3), getValue(values, 4), 
				parseValueToInt("plat", values, 5),
				getValue(values, 6), parseValueToInt("mxr", values, 7), 
				parseValueToInt("mxf", values, 8), 
				getValue(values, 9));

		return parsedTerminal;
	}

	private static Integer parseValueToInt(String fieldName, String[] values, int index) throws EquipmentsApiException {
		String value = getValue(values, index);
		if(value == null || value.trim().isEmpty()) return null;
		try {
			return Integer.valueOf(value);
		} catch(NumberFormatException e) {
			throw new EquipmentsApiException("Invalid value for " + fieldName + ": " + value);
		}
	}
	
	private static String getValue(String[] values, int index) {
		try {
			return values[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;		
		}
	}

}
