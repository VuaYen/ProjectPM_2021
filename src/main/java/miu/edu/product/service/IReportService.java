package miu.edu.product.service;

import miu.edu.product.dto.ReportRequestDTO;
import miu.edu.product.dto.ReportResponseDTO;

public interface IReportService {
    ReportResponseDTO generateOrderReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateVendorReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateProductReport(ReportRequestDTO reportRequestDTO);
    ReportResponseDTO generateCategoryReport(ReportRequestDTO reportRequestDTO);

}
