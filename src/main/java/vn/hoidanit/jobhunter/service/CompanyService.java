package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company saveCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Page<Company> findAllCompany(Specification<Company> spec, Pageable pageable) {
        return this.companyRepository.findAll(spec, pageable);
    }

    public Company findCompanyById(long id) {
        return this.companyRepository.findById(id);
    }

    public void deleteCompanyById(long id) {
        this.companyRepository.deleteById(id);
    }
}
