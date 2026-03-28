package com.security.security_jpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppNavigationController {

    @GetMapping("/auth-signin")
    public String displaySignIn() {
        return "login-view";
    }

    @GetMapping("/dashboard")
    public String displayDashboard() {
        return "main-dashboard";
    }

    @GetMapping("/sys-admin/panel")
    public String displaySupervisorPanel() {
        return "supervisor-panel";
    }

    @GetMapping("/member/panel")
    public String displayMemberPanel() {
        return "member-panel";
    }
}