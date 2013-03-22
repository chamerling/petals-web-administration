package org.ow2.petals.webadministration.utils.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JSFUtils {
    /**
   * Returns the current HTTP session.
   */
  public static HttpSession getHttpSession() {
    FacesContext facescontext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facescontext.getExternalContext().getSession(true);
    return session;
  }

  /**
   * Returns the current servlet response object instance.
   */
  public static HttpServletResponse getHttpServletResponse() {
    FacesContext facescontext = FacesContext.getCurrentInstance();
    HttpServletResponse response=(HttpServletResponse)facescontext.getExternalContext().getResponse();
    return response;
  }

  /**
   * Returns the current servlet request object instance.
   */
  public static HttpServletRequest getHttpServletRequest() {
    FacesContext facescontext = FacesContext.getCurrentInstance();
    HttpServletRequest request=(HttpServletRequest)facescontext.getExternalContext().getRequest();
    return request;
  }

  /**
   * Returns the servlet context.
   */
  public static ServletContext getServletContext() {
    ServletContext context = getHttpSession().getServletContext();
    return context;
  }


	/**
	 * Creates or returns the backing bean.
	 * @param <T>
	 *
	 * @param name
	 *            The name of the bean.
	 * @return The backing bean. (can be null !! so check it)
	 */
	public static <T> T  getBackingBean(String name, Class<T> cls) {
		Object bean = getHttpSession().getAttribute(name);
		return cls.cast(bean);
	}

  public static String redirectPage(String pageWithExtention) {
      FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, pageWithExtention);
      return FacesContext.getCurrentInstance().getApplication().getNavigationHandler().toString();
  }

  public static String redirectPage(String pageWithExtention, String params) {
      FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, pageWithExtention + "?" + params);
      return FacesContext.getCurrentInstance().getApplication().getNavigationHandler().toString();
  }


  /**
   * @param parameterName the parameter name (not null)
   * @return the parameter value (can be null)
   */
  public static String getRequestParameter( String parameterName ) {
	  HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	  return req.getParameter( parameterName );
  }
}
