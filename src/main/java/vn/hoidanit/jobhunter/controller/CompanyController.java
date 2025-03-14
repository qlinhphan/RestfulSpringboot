package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultOfPagination;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.anotation.AnotationRes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company")
    public ResponseEntity<Company> postCompany(@Valid @RequestBody Company company) {
        this.companyService.saveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(company);
    }

    @GetMapping("/company")
    @AnotationRes(value = "Api get users")
    public ResponseEntity<ResultOfPagination> getMethodName(@Filter Specification<Company> spec,
            @RequestParam("page") Optional<String> page,
            @RequestParam("size") Optional<String> size) {
        String Page = page.isPresent() ? page.get() : "";
        String Size = size.isPresent() ? size.get() : "";
        int cPage = Integer.parseInt(Page);
        int cSize = Integer.parseInt(Size);
        Pageable pageable = PageRequest.of(cPage - 1, cSize, Sort.by(Direction.ASC, "name"));
        Page<Company> listC = this.companyService.findAllCompany(spec, pageable);
        List<Company> listComp = listC.getContent();

        Meta meta = new Meta();
        meta.setCurrenPage(cPage);
        meta.setPageSize(cSize);
        meta.setTotalElement(listC.getTotalElements());
        meta.setTotalPage(listC.getTotalPages());

        ResultOfPagination resultOfPagination = new ResultOfPagination();
        resultOfPagination.setMeta(meta);
        resultOfPagination.setResultOfPagina(listComp);

        return ResponseEntity.ok().body(resultOfPagination);
    }

    @PutMapping("/company")
    public ResponseEntity<Company> putMethodNam(@RequestBody Company company) {
        System.out.println("ID: " + company.getId());
        Company c = this.companyService.findCompanyById(company.getId());
        if (c != null) {
            if (company.getName() != null) {
                c.setName(company.getName());
            }
            if (company.getDescription() != null) {
                c.setDescription(company.getDescription());
            }
            if (company.getAddress() != null) {
                c.setAddress(company.getAddress());
            }
            if (company.getLogo() != null) {
                c.setLogo(company.getLogo());
            }
        }
        this.companyService.saveCompany(c);
        return ResponseEntity.ok().body(c);
    }

    @DeleteMapping("/company/{id}")
    public void handleDeleCompany(@PathVariable("id") long id) {
        this.companyService.deleteCompanyById(id);
    }

}
