package com.trsvax.jacquard.environment;


public class PagedLoopEnvironmentImpl<T> implements PagedLoopEnviroment<T>{
	private final Iterable<T> source;
	private final Iterable<T> page;
	private final Integer currentPage;
	private final Integer totalPages;
	private final String parameterName;
	
	public PagedLoopEnvironmentImpl(Iterable<T> source, Iterable<T> page,
			String parameterName, Integer totalPages, Integer currentPage) {
		this.source = source;
		this.page = page;
		this.parameterName = parameterName;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
	}

	public Iterable<T> getSource() {
		return source;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	
	public String getParameterName() {
		return parameterName;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Iterable<T> getPage() {
		return page;
	}

}
