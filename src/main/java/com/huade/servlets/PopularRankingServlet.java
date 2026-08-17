package com.huade.servlets;

import com.google.gson.Gson;
import com.huade.entity.PageResult;
import com.huade.entity.PopularRanking;
import com.huade.entity.ResponseData;
import com.huade.service.PopularRankingService;
import com.huade.service.impl.PopularRankingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet({"/api/ranking", "/api/ranking/*"})
public class PopularRankingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    PopularRankingService service = new PopularRankingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCors(response);
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && !pathInfo.equals("/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                PopularRanking entity = service.findById(id);
                if (entity != null) {
                    response.getWriter().write(gson.toJson(entity));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson(new ResponseData(false, "数据未找到")));
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(new ResponseData(false, "无效的ID")));
            }
            return;
        }

        int page = 1, limit = 10;
        String keyword = request.getParameter("keyword");
        try {
            if (request.getParameter("page") != null) page = Integer.parseInt(request.getParameter("page"));
            if (request.getParameter("limit") != null) limit = Integer.parseInt(request.getParameter("limit"));
        } catch (Exception e) {
            page = 1; limit = 10;
        }
        final int MAX_LIMIT = 100;
        limit = Math.max(1, Math.min(limit, MAX_LIMIT));
        page = Math.max(1, page);

        PageResult<PopularRanking> result = service.search(keyword, page, limit);
        response.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCors(response);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        Gson gson = new Gson();
        PopularRanking entity = gson.fromJson(sb.toString(), PopularRanking.class);
        service.add(entity);
        response.getWriter().write(gson.toJson(new ResponseData(true, "添加成功")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCors(response);
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson(new ResponseData(false, "请求路径必须包含ID")));
            return;
        }
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) sb.append(line);
            }
            Gson gson = new Gson();
            PopularRanking entity = gson.fromJson(sb.toString(), PopularRanking.class);
            entity.setId(id);
            int result = service.modify(entity);
            if (result > 0) {
                response.getWriter().write(gson.toJson(new ResponseData(true, "更新成功")));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(new ResponseData(false, "数据未找到")));
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson(new ResponseData(false, "无效的ID")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCors(response);
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(new Gson().toJson(new ResponseData(false, "请求路径必须包含ID")));
            return;
        }
        int id = Integer.parseInt(pathInfo.substring(1));
        service.remove(id);
        response.getWriter().write(new Gson().toJson(new ResponseData(true, "删除成功")));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleCors(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void handleCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
