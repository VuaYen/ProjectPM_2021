package miu.edu.product.web;

import miu.edu.product.domain.ApiResponse;
import miu.edu.product.service.ReportResponseDTO;
import miu.edu.product.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping({"/", "/data"})
    public ResponseEntity<ApiResponse<ReportResponseDTO>> generateReport() {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try{
            response.setMessage("Successfully");
        }catch (Exception e){
            response.setStatus(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
