var refreshPageInterval = 5000; //30 seconds
var refreshPageIntervalId = 0;
var contextPath;

$(document).ready(
    function() {
      $("#close_message_button").click(function() {
        $(".message").toggleClass("visible-message");
      });

      $("#auto_refresh").click(
          function() {
            if ($("#auto_refresh").is(':checked')) {
              refreshPageIntervalId = setInterval(refreshPage,
                  refreshPageInterval);
            } else {
              clearInterval(refreshPageIntervalId);
            }
          });

      $("#filter_text").change(function() {
        var filterText = $('#filter_text').val();
        filterList(filterText);
      });

      $("#filter_text_button").click(function() {
        var filterText = $('#filter_text').val();
        filterList(filterText);
      });

      $(".dropdown-toggle").click(
          function() {
            $(".dropdown-toggle").next(".dropdown-menu").toggleClass(
                "l--display-block");
            $(".dropdown-toggle").toggleClass("dropdown-toggle-active");
          });
    })

function filterList(filterText) {
  $(".content").html(getLargeLoadingHtml());

  $.ajax({
    url : contextPath + '/filterList',
    cache : false,
    data : ({
      filterText : filterText
    }),
    success : function(response) {
      $(".content").html(response);
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      clearInterval(refreshPageIntervalId);
      console.log(errorThrown);

      displayMessage('error', getErrorMessageHeader(), errorThrown);
    }
  });
}

function refreshPage() {
  $('#refresh_status').html(getLoadingHtml());

  $.ajax({
    url : contextPath + '/updateStatus',
    cache : false,
    success : function(data) {
      for ( var i = 0; i < data.length; i++) {
        var hashCode = data[i].hashCode;
        var status = data[i].status;

        var container = $('#' + hashCode + "-container");
        container.removeClass('Pass Failed Unknown')
        container.addClass(status);

        var statusDiv = container.find("span.status");
        statusDiv.removeClass('icon-checkmark icon-close icon-question');
        statusDiv.html('');

        if (status == 'Pass') {
          statusDiv.addClass('icon-checkmark');
        } else if (status == 'Failed') {
          statusDiv.addClass('icon-close');
        } else if (status == 'Unknown') {
          statusDiv.html(getLoadingHtml());
        }

        var lastCheckDiv = container.find("span.lastChecked");
        lastCheckDiv.html('');
        if (data[i].lastChecked) {
          lastCheckDiv.text(data[i].lastChecked);
        } else {
          lastCheckDiv.html(getLoadingHtml());
        }

        var lastFailedDiv = container.find("td.lastFailed");
        if (data[i].lastFailed) {
          lastFailedDiv.text(data[i].lastFailed);
        } else {
          lastFailedDiv.text("None");
        }
      }
      $('#refresh_status').html(getFormattedDate(new Date()));
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      clearInterval(refreshPageIntervalId);
      displayMessage('error', getErrorMessageHeader(), errorThrown);
      $('#refresh_status').html("");
    }
  });
}

function getFormattedDate(d) {
  var curr_month = d.getMonth() + 1; //Months are zero based
  return d.getDate() + "/" + curr_month + "/" + d.getFullYear() + " "
      + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
}

function getLoadingHtml() {
  return "<img alt='Loading...' src='resources/images/loader.gif'/>";
}

function getLargeLoadingHtml() {
  return "<img alt='Loading...' src='resources/images/loader-large.gif'/>";
}

function getErrorMessageHeader() {
  return "Having problem communicating with server. "
      + "Try <a href='#' onclick='location.reload(true); return false;'>refresh page</a> later.";
}

function displayMessage(severity, messageHeaderHTML, message) {
  if (messageHeaderHTML) {
    clearSeverityMessage();
    if (!$(".message").hasClass("visible-message")) {
      $(".message").addClass("visible-message");
      $(".message").addClass(severity);
    }
    $("#message").html(messageHeaderHTML);

    $('#message_details').text(message);
  }
}

function clearSeverityMessage() {
  $(".message").removeClass("info");
  $(".message").removeClass("warning");
  $(".message").removeClass("error");
}

function toggleDetails(toggleBtn, rowId) {
  var details = $('#' + rowId + "-container").find(".details");
  details.toggleClass("visible");
  $(toggleBtn).toggleClass("rotated");
}

function validateName(name) {
  $.ajax({
    type : "POST",
    url : contextPath + "/validateName",
    data : "name=" + name,
    success : function(response) {
      if (response.status == "Failed") {
        $('#name_error').html(response.result);
        $('#name_error').addClass("l--display-inline-block")
      } else {
        $('#name_error').html('');
        $('#name_error').removeClass("l--display-inline-block")
      }
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      displayMessage('error', getErrorMessageHeader(), errorThrown);
      $('#refresh_status').html("");
    }
  });
}