<%@page import="com.daw.Controladores.CriterioController"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.daw.DAO.CriterioEvaluacionDAO"%>
<%@page import="com.daw.DAO.ComponenteBBDD"%>
<%@page import="com.daw.modelo.CriterioEvaluacion"%>
<%@page import="com.daw.modelo.Asignatura"%>
<%@page import="com.daw.modelo.ResultadoAprendizaje"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Criterios de Evaluación</title>
        <link href="css/stylesAs.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@ include file="include/header.jsp" %>        
        <div class="container">

            <%
                CriterioController criterioController = new CriterioController();
                List<Object> criterios = null;
                String[] resultados = null;
                String selected = null;
                String nombreAsignatura = request.getParameter("nombreAsignatura");
                String nombreResultado = request.getParameter("resultado");

                if (nombreAsignatura != null && !nombreAsignatura.isEmpty()) {
                    resultados = criterioController.obtenerRA(nombreAsignatura);
                }

                if (nombreAsignatura != null && nombreResultado != null) {
                    criterios = criterioController.obtenerCriterios(nombreAsignatura, nombreResultado);
                }
            %>

            <h1>Filtrar Criterios de Evaluación</h1>
            <div class="subject-filter">
                <!-- Formulario unificado para Asignatura y Resultado -->
                <form method="get" action="criterios2.jsp" id="filtroForm">
                    <label for="nombreAsignatura">Selecciona una Asignatura:</label>
                    <select name="nombreAsignatura" id="nombreAsignatura" onchange="this.form.submit()"> 
                        <option value="">Seleccione...</option>
                        <%
                            String[] asignaturas = criterioController.obtenerAsignaturas();
                            for (String asignatura : asignaturas) {
                                selected = (asignatura.equals(nombreAsignatura)) ? "selected" : "";
                        %>
                        <option value="<%= asignatura%>" <%= selected%>><%= asignatura%></option>
                        <%
                            }
                        %>
                    </select>

                    <label for="resultado">Seleccionar Resultado de Aprendizaje:</label>
                    <select name="resultado" id="resultado" onchange="this.form.submit()"> 
                        <option value="">Seleccione...</option>
                        <%
                            if (resultados != null) {
                                for (String resultado : resultados) {
                                    selected = (resultado.equals(nombreResultado)) ? "selected" : "";
                        %>
                        <option value="<%= resultado%>" <%= selected%>><%= resultado%></option>
                        <%
                                }
                            }
                        %>
                    </select>

                </form>
            </div>
            <!-- Tabla de Criterios de Evaluación -->
            <% if (criterios != null && !criterios.isEmpty()) { %>           
            <div class="subject-table">
                <table>
                    <thead>
                        <tr>
                            <th>ID Criterio</th>
                            <th>Resultado</th>
                            <th>Asignatura</th>
                            <th>Porcentaje</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Object obj : criterios) {
                                if (obj instanceof CriterioEvaluacion) {
                                    CriterioEvaluacion criterio = (CriterioEvaluacion) obj;
                        %>
                        <tr>
                            <td><%= criterio.getIdCriterio()%></td>
                            <td><%= nombreResultado%></td>
                            <td><%= nombreAsignatura%></td>
                            <td><%= criterio.getPorcentaje()%></td>
                            <td><%= criterio.getNombre()%></td>
                            <td class="actions-cell">
                                <div class="d-inline">
                                    <button type="submit" class="edit-btn" onclick="abrirModal('<%= criterio.getIdCriterio()%>', '<%= criterio.getIdResultado()%>', '<%= criterio.getIdAsignatura()%>', '<%= criterio.getNombre()%>', '<%= criterio.getPorcentaje()%>')">Editar</button>
                                </div>
                                <form action="criterios.jsp">
                                    <input type="hidden" name="accion" value="borrar">
                                    <input type="hidden" name="id_criterio" value="<%= criterio.getIdCriterio()%>">
                                    <button type="submit" class="delete-btn">Borrar</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
            <% }%>

            <% if (nombreAsignatura != "Seleccione..." && nombreAsignatura != null && !nombreAsignatura.isEmpty() && nombreResultado != null && nombreResultado!= "Seleccione..." && !nombreResultado.isEmpty()) {%>
            <div class="form-container">
                <h2>Agregar Criterio</h2>
                <form method="get" action="criterios.jsp">
                    <input type="hidden" name="accion" value="agregar">
                    <div class="input-group">
                        <label>ID Asignatura:</label>
                        <input type="text" name="id_asignatura" value="<%= criterioController.obtenerIdAsignatura(nombreAsignatura)%>" readonly>
                    </div>
                    <div class="input-group">
                        <label>ID Resultado:</label>
                        <input type="text" name="id_resultado" value="<%= criterioController.obtenerIdRA(nombreResultado)%>" readonly>
                    </div>
                    <div class="input-group">
                        <label>ID Criterio:</label>
                        <input type="number" name="id_criterio" required>
                    </div>
                    <div class="input-group">
                        <label>Porcentaje:</label>
                        <input type="number" step="0.01" name="porcentaje" required>
                    </div>
                    <div class="input-group">
                        <label>Nombre:</label>
                        <input type="text" name="nombre" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Agregar</button>
                </form>
            </div>
            <% }%>



            <!-- Modal de edición -->
            <div id="modalEditar" class="modal">            
                <div class="modal-content">
                    <span class="close" onclick="cerrarModal()">&times;</span>
                    <h2 class="tituloModal">Editar Criterio</h2>
                    <form method="post" action="criterios.jsp">
                        <input type="hidden" name="id_criterio" id="modalIdCriterio">
                        <label  class="modal-intro-nombre-label" for="modalRA">Id Resultado Aprendizaje</label>
                        <input type="number" name="RA" id="modalRA" class="modal-intro-nombre">
                        <label class="modal-intro-nombre-label" for="modalIdAsignatura">Id Asignatura</label>
                        <input type="number" name="idAsignatura" id="modalIdAsignatura" class="modal-intro-nombre">
                        <label class="modal-intro-nombre-label" for="modalNombre">Nombre:</label>
                        <input type="text" name="nombre" id="modalNombre" class="modal-intro-nombre" >
                        <label class="modal-intro-nombre-label" for="modalPorcentaje">Porcentaje:</label>
                        <input type="number" step="0.01" name="porcentaje" id="modalPorcentaje"  class="modal-intro-nombre">
                        <button type="submit" name="accion" value="editar">Guardar Cambios</button>
                    </form>
                </div>
            </div>

        </div>

        <%@ include file="include/footer.jsp" %>

        <script>

            // Funciones del modal
            function abrirModal(idCriterio, idRa, idAsignatura, nombre, porcentaje) {
                document.getElementById('modalIdCriterio').value = idCriterio;
                document.getElementById('modalRA').value = idRa;
                document.getElementById('modalIdAsignatura').value = idAsignatura;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalPorcentaje').value = porcentaje;
                document.getElementById('modalEditar').style.display = 'block';
            }

            function cerrarModal() {
                document.getElementById('modalEditar').style.display = 'none';
            }

            window.onclick = function (event) {
                var modal = document.getElementById('modalEditar');
                if (event.target === modal) {
                    cerrarModal();
                }
            }
        </script>
    </body>
</html>