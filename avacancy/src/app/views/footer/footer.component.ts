import {Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {AboutDialogComponent} from "../../dialog/about/about-dialog.component";

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.css']
})

// "presentational component": отображает полученные данные
// подвал - нижняя часть страницы
export class FooterComponent implements OnInit {
    year: Date;
    site = 'http://otus.ru/';
    siteName = 'OTUS';

    constructor(private  dialog: MatDialog) {
    }

    ngOnInit() {
        this.year = new Date(); // текуший год
    }

    // окно "О программе"
    openAboutDialog() {
        this.dialog.open(AboutDialogComponent,
            {
                autoFocus: false,
                data: {
                    dialogTitle: 'О программе',
                    message: 'Поиск вакансий'
                },
                width: '400px'
            });

    }

}
