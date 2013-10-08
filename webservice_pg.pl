#!/usr/bin/perl -w
#
use DBI;
use Data::Dumper;

 {
 package MyWebServer;
 
 use HTTP::Server::Simple::CGI;
 use base qw(HTTP::Server::Simple::CGI);
 
 my %dispatch = (
     '/hello' => \&resp_hello,
     '/reverse' => \&reverse
 );
 
 sub handle_request {
     my $self = shift;
     my $cgi  = shift;
   
     my $path = $cgi->path_info();
     my $handler = $dispatch{$path};
 
     if (ref($handler) eq "CODE") {
         print "HTTP/1.0 200 OK\r\n";
         $handler->($cgi);
         
     } else {
         print "HTTP/1.0 404 Not found\r\n";
         print $cgi->header,
               $cgi->start_html('Not found'),
               $cgi->h1('Not found'),
               $cgi->end_html;
     }
 }
 
 sub resp_hello {
     my $cgi  = shift;   # CGI.pm object
     return if !ref $cgi;
     
     my $who = $cgi->param('name');
     
     print $cgi->header,
           $cgi->start_html("Hello"),
           $cgi->h1("Hello $who!"),
           $cgi->end_html;
 }

 sub reverse
 {
 	my $cgi = shift;
	my $a_inverser=$cgi->param('chaine');

	my $dbh=DBI->connect('dbi:Pg:port=6667') or die;
	my $query="select row_to_json(row(string_agg(tmp2.regexp_matches,''))) from (SELECT regexp_matches[1],row_number from (SELECT *,row_number() over () from regexp_matches(?,'.','g')) as tmp1 order by 2 desc) as tmp2";
	my $sth=$dbh->prepare($query);
	$sth->execute($a_inverser);
	my $result=$sth->fetchall_arrayref();
	print $cgi->header,$result->[0]->[0];
 }
 
 } 
 
 # start the server on port 8080
 my $pid = MyWebServer->new(8080)->run();

#select row_to_json(row(string_agg(tmp2.regexp_matches,''))) from (SELECT regexp_matches[1],row_number from (SELECT *,row_number() over () from regexp_matches('tati','.','g')) as tmp1 order by 2 desc) as tmp2;

