function busca() {
    $("#busca").on("input", function (data) {
        var instrumentos = $("#checkInstrumentos").is(":checked");
        var estilos = $("#checkEstilos").is(":checked");
        var interesse = $("#checkInteresse").is(":checked");
        var anuncios = $(".anuncio");
        var buscaInput = $("#busca").val().toLowerCase();
        var buscaInputTermos = buscaInput.split(/\s*,\s*/);
        var anuncio;
        var resultado;

        buscaInputTermos[buscaInputTermos.length - 1] = buscaInputTermos[buscaInputTermos.length - 1].trim();

        for (var i = 0; i < anuncios.length; i++) {
            anuncio = $(anuncios[i]);
            resultado = true;

            if (instrumentos) {
                for (var j = 0; j < buscaInputTermos.length; j++) {
                    if ((anuncio.find(".anuncioInstrumentos").text().toLowerCase()).indexOf(buscaInputTermos[j]) == -1) {
                        resultado = false;
                    } else {
                        resultado = true;
                        break;
                    }
                }
            }

            if (estilos) {
                for (var j = 0; j < buscaInputTermos.length; j++) {
                    if ((anuncio.find(".anuncioEstilos").text().toLowerCase()).indexOf(buscaInputTermos[j]) == -1) {
                        resultado = false;
                    } else {
                        resultado = true;
                        break;
                    }
                }
            }

            if (interesse) {
                for (var j = 0; j < buscaInputTermos.length; j++) {
                    if ((anuncio.find(".anuncioInteresse").text().toLowerCase()).indexOf(buscaInputTermos[j]) == -1) {
                        resultado = false;
                    } else {
                        resultado = true;
                        break;
                    }
                }
            }

            if (!instrumentos && !estilos && !interesse) {
                for (var j = 0; j < buscaInputTermos.length; j++) {
                    if ((anuncio.text().toLowerCase()).indexOf(buscaInputTermos[j]) == -1) {
                        resultado = false;
                    }
                }
            }

            if (resultado) {
                anuncio.show();
            } else {
                anuncio.hide();
            }
        }
    })
}

$(function () {
    busca();

    $('#btnToggle').click(function () {
        if ($(this).hasClass('on')) {
            $('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
            $(this).removeClass('on');
        }
        else {
            $('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
            $(this).addClass('on');
        }
    });
});
