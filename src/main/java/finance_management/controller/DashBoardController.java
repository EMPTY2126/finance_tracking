package finance_management.controller;

import finance_management.dto.dashboard.DashBoardResponse;
import finance_management.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashboardService dashboardService;

    public DashBoardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashBoardResponse getDashboardDetails(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ){
       return dashboardService.getDashboardDetails(month, year);
    }
}
