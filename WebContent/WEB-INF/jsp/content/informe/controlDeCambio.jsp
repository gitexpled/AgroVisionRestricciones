<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <script src="https://cdn.tailwindcss.com"></script>
  <div class="bg-white shadow rounded-lg p-6 mb-6">
    <h2 class="text-lg font-semibold mb-4">Histórico de Versiones de LMR</h2>
    <div class="flex flex-wrap items-center gap-4 mb-4">
      <label class="font-medium">Rango de Fecha:</label>
      <input type="date" id="startDate" class="rounded px-3 py-1" value="2025-01-01">
      <span class="font-bold">a</span>
      <input type="date" id="endDate" class="rounded px-3 py-1" value="2025-01-10">
      <label class="flex items-center">
        <input type="checkbox" id="onlyChanges" class="mr-2">
        Solo con cambios
      </label>
      <button id="btnBuscar" class="bg-blue-600 text-white px-4 py-2 hover:bg-blue-700">
        Buscar
      </button>
    </div>
  </div>
  <div class="bg-white shadow rounded-lg overflow-auto">
    <table class="min-w-full text-left">
      <thead class="bg-gray-50 border-b text-gray-700 font-medium">
        <tr>
          <th class="px-4 py-2">Fecha</th>
          <th class="px-4 py-2">Estado</th>
          <th class="px-4 py-2">Detalle</th>
          <th class="px-4 py-2">Opción</th>
        </tr>
      </thead>
      <tbody id="versionTable">
      </tbody>
    </table>
  </div>
  <div  id="modal" class="fixed inset-0 bg-black bg-opacity-30 hidden items-center justify-center z-50">
    <div class="bg-white rounded-lg w-[600px] p-6 shadow-lg relative border-gray-300">
      <button id="closeModal" class="absolute top-2 right-3 text-gray-500 hover:text-black text-bold">X</button>
      <h3 class="text-3xl font-semibold mb-2">Detalle de cambios</h3>
      <div class="text-gray-700 text-2xl whitespace-pre-line" id="modalContent">
      </div>
    </div>
  </div>
