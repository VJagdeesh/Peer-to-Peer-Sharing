package com.example.util;
import com.example.daos.BookDao;
import com.example.daos.FirestoreDao;
import com.google.common.base.Strings;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
public class BSListener implements ServletContextListener {
  @Override
public void contextDestroyed(javax.servlet.ServletContextEvent event) {
  }
  @Override
public void contextInitialized(ServletContextEvent event) {
TBScript d = (TBScript) event.getServletContext().getAttribute("dao");
if (d == null) {
      d = new Fire_Store();
event.getServletContext().setAttribute("dao", d);
    }
Boolean isCloudStorageConfigured = (Boolean) event.getServletContext().getAttribute("isCloudStorageConfigured");
if (isCloudStorageConfigured == null) {
event.getServletContext().setAttribute("isCloudStorageConfigured",!Strings.isNullOrEmpty(System.getenv("BOOKSHELF_BUCKET")));
    }
CloudH storageHelper = (CloudH) event.getServletContext().getAttribute("storageHelper");
if (storageHelper == null) {
storageHelper = new CloudH();
event.getServletContext().setAttribute("storageHelper", storageHelper);
    }
  }
}

