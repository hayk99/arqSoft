#!/usr/bin/env ruby
require 'bunny'

# connection = Bunny.new
connection = Bunny.new(hostname: 'rdp02.unizar.es', user: 'alumno', pass: 'alumno')
connection.start

channel = connection.create_channel
exchange = channel.fanout('as1')

message = ARGV.empty? ? 'Hello World!' : ARGV.join(' ')

exchange.publish(message)
puts " [x] Sent #{message}"

connection.close