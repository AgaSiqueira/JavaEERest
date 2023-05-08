/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import org.json.JSONObject;
import model.User;
import org.json.JSONArray;



/**
 *
 * @author agath
 */
@WebServlet(name = "TasksServlet", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {

     private JSONObject getJSONBody(BufferedReader reader) throws Exception{
         StringBuilder buffer = new StringBuilder();
         String line = null;
         while((line = reader.readLine()) != null){
             buffer.append(line);
         }
         return new JSONObject(buffer.toString());
     }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("application/json;charset = utf-8");
       JSONObject file = new JSONObject();
       try{
           file.put("list", new JSONArray(User.list));
       }catch(Exception ex){
           response.setStatus(500);
           file.put("error", ex.getLocalizedMessage());
       }
       response.getWriter().print(file.toString());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("application/json;charset = utf-8");
       JSONObject file = new JSONObject();
       try{
           JSONObject body = getJSONBody(request.getReader());
           String name = body.getString("name");
           String email = body.getString("email");
           String senha = body.getString("senha");
           if(name != null && email != null && senha != null){
               User t = new User(name,email,senha);
               User.list.add(t);
           }
           file.put("list", new JSONArray(User.list));
       }catch(Exception ex){
           response.setStatus(500);
           file.put("error", ex.getLocalizedMessage());
       }
       response.getWriter().print(file.toString());
    }
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("application/json;charset = utf-8");
       JSONObject file = new JSONObject();
       try{
           String title = request.getParameter("title");
           int i = -1;
           for(User t: User.list){
               if(t.getName().equals(title) || t.getEmail().equals(title)){
                i= User.list.indexOf(t);
                break;
               }
           }
           if (i>-1) User.list.remove(i);
           file.put("list", new JSONArray(User.list));
       }catch(Exception ex){
           response.setStatus(500);
           file.put("error", ex.getLocalizedMessage());
       }
       response.getWriter().print(file.toString());
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
