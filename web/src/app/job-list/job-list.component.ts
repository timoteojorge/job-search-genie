import { Component, OnInit, Input } from '@angular/core';
import { Job } from '../model/job';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss']
})
export class JobListComponent implements OnInit {

  @Input() set list(list: Job[]) {
    if (list) {
      this.rows = [];
      list.forEach((job, index) => {
        let currentRow = Math.floor(index / 3);
        if (!this.rows[currentRow]) {
          this.rows[currentRow] = [];
        }
        this.rows[currentRow].push(job);
      });
      console.log(this.rows);
    }
  }
  rows: Job[][];

  constructor() { }

  ngOnInit(): void {
  }

  getDescription(job: Job) {
    if (job && job.description) {
      if (job.description.length >= 300) {
        return job.description.substr(0, 300).concat('...');
      }
      return job.description;
    }
    return '';
  }

  getTitle(job: Job) {
    if (job && job.title ) {
      if (job.title.length >= 20) {
        return job.title.substring(0, 20).concat('...');
      }
      return job.title;
    }
    return '';
  }

  getSubtitle(job: Job) {
    if (job && job.company && job.company.display_name) {
      if (job.company.display_name.length >= 22) {
        return job.company.display_name.substring(0, 22).concat('...');
      }
      return job.company.display_name;
    }
    return '-';
  }

  apply(job: Job) {
    window.open(job.redirect_url,'_blank');
  }

}
