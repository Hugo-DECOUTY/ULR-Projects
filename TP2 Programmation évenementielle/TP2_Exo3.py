import sys
import urllib.request
from datetime import datetime

from PyQt5.QtWebEngineWidgets import QWebEngineView
from PyQt5.QtWidgets import *
from PyQt5.QtGui import *
from PyQt5.QtCore import *

class myBrowserWindow(QWidget):
    def __init__(self, myWindows, parent=None):
        QWidget.__init__(self, parent=parent)

        self.myBrowserWidget = QWebEngineView(self)
        self.url = "https://www.pokepedia.fr/Liste_des_Pokémon_par_ordre_alphabétique"
        self.myBrowserWidget.load(QUrl(self.url))
        self.setFixedSize(800, 600)
        self.titlestart = self.url
        self.setWindowTitle(self.titlestart)
        myWindows.sendLetter.connect(self.modifierLettre)

    def modifierLettre(self, lettre):
        urlIndex = self.url + "#" + str(lettre)
        self.myBrowserWidget.load(QUrl(urlIndex))



class myMainWindow(QWidget):
    sendLetter = pyqtSignal(str)  # Par défaut

    def __init__(self, parent=None):
        QWidget.__init__(self, parent=parent)

        # attributs de la fenetre principale
        self.setGeometry(300, 300, 800, 400)
        self.titlestart = "DECOUTY Hugo " + urllib.request.urlopen('http://bot.whatismyipaddress.com/').read().decode(
            'utf8') + datetime.now().strftime("  %H:%M:%S")
        self.setWindowTitle(self.titlestart)
        self.rectUtil = self.rect()
        self.x = 0
        self.y = 0
        self.setMinimumSize(400, 200)
        self.string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        self.initUI()  # appel d'une methode dédiée à la création de l'IHM

    def dessine(self):
        self.doPaint = True
        self.update()

    def efface(self):
        self.doPaint = False
        self.update()

    def keyPressEvent(self, event):
        if event.key() == Qt.Key_D:
            self.dessine()
        if event.key() == Qt.Key_E:
            self.efface()

    def paintEvent(self, event):
        qp = QPainter()
        qp.begin(self)
        self.setWindowTitle(self.titlestart + " " + str(self.width()) + " " + str(self.height()) + " " + str(self.lettreClick))
        if self.doPaint:
            width, height = self.width(), self.height()
            self.rectUtil = self.rect()

            # Tracé de la ligne horizontale
            qp.drawLine(0, height / 2, width, height / 2)

            # Tracé de la ligne verticale
            nb_lettres_par_ligne = 13
            for i in range(1, nb_lettres_par_ligne+1):
                step = (width / nb_lettres_par_ligne) * i
                last_step = (width / nb_lettres_par_ligne) * (i-1)

                qp.drawLine(step, height, step, 0)
                qp.drawText(int((step+last_step)/2), int(self.height()/4), self.string[i-1])
                qp.drawText(int((step+last_step)/2), int((self.height()/4)*3), self.string[(nb_lettres_par_ligne+i)-1])
        else:
            qp.eraseRect(self.rectUtil)

        qp.end()

    def mouseReleaseEvent(self, event):
        p = event.pos()

        self.x = event.x()
        self.y = event.y()

        nb_lettres_par_ligne = 13

        step = self.width()/nb_lettres_par_ligne

        #On cherche si l'élément est placé au dessus du milieu ou pas.
        if self.y < self.height()/2:
            #Division entière où on récupère la lettre en fonction de la position de la souris par rapport à la valeur d'intervalle.
            pos_lettre = self.x // step
        else:
            pos_lettre = nb_lettres_par_ligne + (self.x // step)

        #On récupère la lettre dans le string en fonction de l'index pos_lettre.
        self.lettreClick = self.string[int(pos_lettre)]
        self.sendLetter.emit(self.lettreClick)
        self.update()

    def initUI(self):
        # L'affichage sur la console peut-être supprimé, il faudra completer ici pour la création d'autre elements IHM
        w = self.width()
        h = self.height()
        self.lettreClick = 'NA'
        self.doPaint = True


if __name__ == '__main__':
    app = QApplication(sys.argv)
    w = myMainWindow()
    b = myBrowserWindow(w)
    w.show()
    b.show()
    app.exec_()  # ou de préférence sys.exit(app.exec_()) si vous êtes sous linux